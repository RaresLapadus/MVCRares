package ro.teamnet.zth.app;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rares on 5/6/2015.
 */
@WebServlet(name = "MyDispatcherServlet")
public class MyDispatcherServlet extends HttpServlet {


    Map<String,MethodAttributes> allowedMethods = new HashMap<>();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("GET", req, resp);
    }

    @Override
    public void init() throws ServletException {
        try {
           Iterable<Class> classes =  AnnotationScanUtils.getClasses("ro.teamnet.zth.app.controller");
            allowedMethods = getAllowedRequestMethods(classes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, MethodAttributes> getAllowedRequestMethods(Iterable<Class> classes) {

        Map<String, MethodAttributes> hashMap = new HashMap<>();
        for(Class controller :classes){
            if(controller.isAnnotationPresent(MyController.class)){
                MyController myCtrlAnnotation = (MyController)controller.getAnnotation(MyController.class);
                String controllerUrlPath = myCtrlAnnotation.urlPath();
                Method[] controllerMethods = controller.getMethods();
                for (Method controllerMethod : controllerMethods) {
                    if(controllerMethod.isAnnotationPresent(MyRequestMethod.class)){

                        MyRequestMethod methodAnnotation = (MyRequestMethod)controllerMethod.getAnnotation(MyRequestMethod.class);
                        String key = controllerUrlPath + methodAnnotation.urlPath();

                        MethodAttributes methodAttributes = new MethodAttributes();

                        methodAttributes.setControllerClass(controller.getName());
                        methodAttributes.setMethodName(controllerMethod.getName());
                        methodAttributes.setMethodType(methodAnnotation.methodType());
                        methodAttributes.setParamTypes(controllerMethod.getParameterTypes());

                        hashMap.put(key,methodAttributes);
                    }
                }
            }
        }
        return hashMap;
    }

    protected void dispatchReply(String method, HttpServletRequest req, HttpServletResponse resp){


        try{
            Object dispatch = dispatch(req, resp);
            reply(dispatch,req,resp);
        }catch (IOException e){
            e.printStackTrace();
        }catch (DispatchException e){
            try {
                sendException(e, resp);
            }
            catch (IOException excep){
                excep.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    protected Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String pathInfo = req.getPathInfo();

        /*if (pathInfo.startsWith("/employees")) {

            EmployeeController employeeController = new EmployeeController();
            String allEmplyees = employeeController.getAllEmployees();
            return allEmplyees;

        }else if(pathInfo.startsWith("/departments")){
            DepartmentController departmentController = new DepartmentController();
            String allDepartment = departmentController.getAllDepartments();
            return allDepartment;
        }else if(pathInfo.startsWith("/jobs")) {
            JobsController jobsController = new JobsController();
            String allJobs = jobsController.getAllJobs();
            return allJobs;
        }else if(pathInfo.startsWith("/locations")) {
            LocationsController locationsController = new LocationsController();
            String allLocations = locationsController.getAllLocations();
            return allLocations;
        }
        throw new DispatchException();*/

        MethodAttributes methodAttributes = allowedMethods.get(pathInfo);

        if(methodAttributes != null) {

            Object o = Class.forName(methodAttributes.getControllerClass()).newInstance();

            Method method = o.getClass().getMethod(methodAttributes.getMethodName(),methodAttributes.getParamTypes());

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            if(parameterAnnotations.length > 0 && parameterAnnotations[0][0] != null) {
                MyRequestParam annotation = (MyRequestParam) parameterAnnotations[0][0];


                List<String> methodParamsValues = new ArrayList<>();

                String valueOfParamName = req.getParameter(annotation.paramName());

                methodParamsValues.add(valueOfParamName);
                return method.invoke(o,(String[]) methodParamsValues.toArray(new String[0]));
            }else{
                return method.invoke(o);
            }



        }


        return "Hello!";

    }

    protected void reply(Object result ,HttpServletRequest req, HttpServletResponse resp ) throws IOException {

        PrintWriter printWriter = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        printWriter.printf(mapper.writeValueAsString(result));


    }

    private void sendException(DispatchException dispatch ,HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.printf("ERROR");
    }


}

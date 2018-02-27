package com.example.kartik.tutorfinder.LoginRegister;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kartik on 14-12-2017.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://tutorsfind.000webhostapp.com/Mobile/register.php";
    private Map<String, String> params;
    public RegisterRequest(String name,String email, String password,String gender, int age, String address, String mobile,
                           Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener, null);
        params= new HashMap<>();
        params.put("name", name);
        params.put("email",email);
        params.put("password",password);
        params.put("gender", gender);
        params.put("age",age+"");
        params.put("address",address);
        params.put("mobile",mobile);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


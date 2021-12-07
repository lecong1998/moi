package ie.app.uetstudents.ui.login.Logout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Account.Get.dangky.dangky_account
import ie.app.uetstudents.ui.Entity.Account.Post.account

import kotlinx.android.synthetic.main.fragment_logout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogoutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            register_fragment_btn_register.setOnClickListener {


                if (register_fragment_edt_username.text.toString().isEmpty()
                    ||register_fragment_edt_password.text.toString().isEmpty()
                    ||register_fragment_edt_confirmpass.text.toString().isEmpty())
                {
                    Toast.makeText(context,"Thông tin chưa đầy đủ",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                 if(register_fragment_edt_password.text.toString().equals(register_fragment_edt_confirmpass.text.toString()))
                {
                    callApi(register_fragment_edt_username.text.toString(),
                        register_fragment_edt_password.text.toString()
                    )
                }
                else{
                    Toast.makeText(context,"Mật khẩu nhập không khớp!",Toast.LENGTH_LONG).show()
                     return@setOnClickListener
                }

            }


        register_fragment_btn_cancel?.setOnClickListener {
            it.findNavController().navigate(R.id.action_logoutFragment_to_loginFragment)
        }
    }

    private fun callApi(username: String, password: String) {

        val call : Call<dangky_account> = ApiClient.getClient.CallsetAccount(account(username,password))
        call.enqueue(object : Callback<dangky_account>{
            override fun onResponse(
                call: Call<dangky_account>,
                response: Response<dangky_account>
            ) {
                if (response.isSuccessful)
                {
                    if (response.body()!!.success==true)
                    {
                        Toast.makeText(context,response.body()!!.message,Toast.LENGTH_SHORT).show()
                        this@LogoutFragment.findNavController().navigate(R.id.action_logoutFragment_to_loginFragment)
                        Log.e("đăng ký","Thành công")
                        return
                    }
                    if(response.body()!!.success==false)
                    {
                        Toast.makeText(context,response.body()!!.message,Toast.LENGTH_SHORT).show()
                        Log.e("đăng ký"," tài khoản đã tồn tại")
                        return
                    }


                }
            }

            override fun onFailure(call: Call<dangky_account>, t: Throwable) {
                Log.e("đăng ký","Thất bại")
            }
        })



    }

}
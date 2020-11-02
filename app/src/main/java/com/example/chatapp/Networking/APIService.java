package com.example.chatapp.Networking;

import com.example.chatapp.Notification.MyResponse;
import com.example.chatapp.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {"Content-Type:application/json",
                    "Authorization:key=AAAAVRWKIX0:APA91bGz6HdvbieQTU_r3HkS0v6dMEel5BIP2l0oujR2YR-RmKe7rAmTa_XVXJa3JpXrkwc_nDIzCVgWRPYbMltnxgDK7HkgKy7C-KtC2XdaIcVua_ned6XtUOyi2NLruiCdmdirfZF0"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

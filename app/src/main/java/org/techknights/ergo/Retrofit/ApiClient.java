package org.techknights.ergo.Retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Hansa on 1/19/2018.
 */

public interface ApiClient {

    @GET("/api/v1/teamMembers/{userId}")
    Call<ArrayList<GroupMembers>> getPeople(@Header("Authorization") String authorization, @Path("userId") String userId);

    @GET("/api/v1/users/{userId}")
    Call<ArrayList<ProfileData>> getProfileData(@Header("Authorization") String authorization, @Path("userId") String userId);

    @GET("/api/v1/userTasks/{userId}")
    Call<ArrayList<TasksofUser>> getTask(@Header("Authorization") String authorization, @Path("userId") String userId);

    @GET("/api/v1/tasks/{userId}")
    Call<ArrayList<TaskData>> getTaskData(@Header("Authorization") String authorization, @Path("userId") String taskID);



}

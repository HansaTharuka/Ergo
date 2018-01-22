package org.techknights.ergo.Retrofit;

import org.techknights.ergo.Retrofit.ListViews.EventsOfUser;
import org.techknights.ergo.Retrofit.ListViews.GroupMembers;
import org.techknights.ergo.Retrofit.ListViews.MilestonesOfUser;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;
import org.techknights.ergo.Retrofit.ListViews.TasksofUser;
import org.techknights.ergo.Retrofit.SingleViews.EventData;
import org.techknights.ergo.Retrofit.SingleViews.MilestoneData;
import org.techknights.ergo.Retrofit.SingleViews.ProfileData;
import org.techknights.ergo.Retrofit.SingleViews.ProjectData;
import org.techknights.ergo.Retrofit.SingleViews.TaskData;

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

    @GET("/api/v1/userProjects/{userId}")
    Call<ArrayList<ProjectOfUser>> getProjectsOfUser(@Header("Authorization") String authorization, @Path("userId") String userId);

    @GET("/api/v1/projects/{userId}")
    Call<ArrayList<ProjectData>> getProjectData(@Header("Authorization") String authorization, @Path("userId") String projectId);

    @GET("/api/v1/userEvents/{userId}")
    Call<ArrayList<EventsOfUser>> getEventsOfUser(@Header("Authorization") String authorization, @Path("userId") String eventId);

    @GET("/api/v1/events/{userId}")
    Call<ArrayList<EventData>> getEventData(@Header("Authorization") String authorization, @Path("userId") String eventId);

    @GET("/api/v1/userMilestones/{userId}")
    Call<ArrayList<MilestonesOfUser>> getMilestonesOfUser(@Header("Authorization") String authorization, @Path("userId") String
            milestoneId);

    @GET("/api/v1/milestones/{userId}")
    Call<ArrayList<MilestoneData>> getMilestoneData(@Header("Authorization") String authorization, @Path("userId") String
            milestoneId);



}

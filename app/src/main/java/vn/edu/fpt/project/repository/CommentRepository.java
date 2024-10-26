package vn.edu.fpt.project.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.fpt.project.model.Comment;
import vn.edu.fpt.project.network.ApiClient;
import vn.edu.fpt.project.network.ApiService;

public class CommentRepository {

    private ApiService apiService;

    public CommentRepository() {
        apiService = ApiClient.getApiService();
    }

    public LiveData<List<Comment>> getComments(String productId) {
        MutableLiveData<List<Comment>> data = new MutableLiveData<>();
        apiService.getComments(productId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }
}

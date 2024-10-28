package vn.edu.fpt.project.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.fpt.project.model.Product;
import vn.edu.fpt.project.network.ApiClient;
import vn.edu.fpt.project.network.ApiService;

public class ProductRepository {

    private ApiService apiService;

    public ProductRepository() {
        apiService = ApiClient.getApiService();
    }

    public LiveData<List<Product>> getAllProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
//                    // Delay the response by 2 seconds
//                    new Handler().postDelayed(() -> {
//                        data.setValue(response.body());
//                    }, 2000); // 2000 milliseconds = 2 seconds

                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }

    public LiveData<List<Product>> getFeaturedProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiService.getFeaturedProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }

    public LiveData<List<Product>> getLatestProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiService.getLatestProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }

    public LiveData<List<Product>> getRelatedProducts(String id) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiService.getRelatedProducts(id).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }

    public LiveData<Product> getProductById(String id) {
        MutableLiveData<Product> data = new MutableLiveData<>();
        apiService.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }

    public LiveData<List<Product>> searchProducts(String filter, String keyword) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiService.searchProducts(filter, keyword).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle failure
            }
        });
        return data;
    }
}

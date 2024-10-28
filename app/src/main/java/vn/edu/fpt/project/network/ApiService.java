package vn.edu.fpt.project.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.fpt.project.model.Comment;
import vn.edu.fpt.project.model.Product;

public interface ApiService {
    @GET("products?filter=latest")
    Call<List<Product>> getAllProducts();

    @GET("products")
    Call<List<Product>> searchProducts(@Query("filter") String filter, @Query("search") String keyword);

    @GET("products?filter=featured")
    Call<List<Product>> getFeaturedProducts();

    @GET("products?filter=latest&pageSize=5")
    Call<List<Product>> getLatestProducts();

    @GET("products/{id}/related")
    Call<List<Product>> getRelatedProducts(@Path("id") String id);

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @GET("products/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") String id);
}

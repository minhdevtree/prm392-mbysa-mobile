package vn.edu.fpt.project.model;

public class Comment {
    private String id;
    private String productId;
    private String content;
    private String rate;
    private String createdAt;

    public Comment(String id, String productId, String content, String rate, String createdAt) {
        this.id = id;
        this.productId = productId;
        this.content = content;
        this.rate = rate;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

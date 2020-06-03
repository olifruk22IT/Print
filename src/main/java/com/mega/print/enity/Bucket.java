package com.mega.print.enity;




import java.time.LocalDateTime;


public class Bucket {
    private Long id;
    private Long user_id;
    private LocalDateTime date_bucket;
    private int sum_bucket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getDate_bucket() {
        return date_bucket;
    }

    public void setDate_bucket(LocalDateTime date_bucket) {
        this.date_bucket = date_bucket;
    }

    public int getSum_bucket() {
        return sum_bucket;
    }

    public void setSum_bucket(int sum_bucket) {
        this.sum_bucket = sum_bucket;
    }
}

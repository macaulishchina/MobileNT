package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/7.
 */

public class GetImageUrl2 {


    private List<CityImageBean> CityImage;

    public List<CityImageBean> getCityImage() {
        return CityImage;
    }

    public void setCityImage(List<CityImageBean> CityImage) {
        this.CityImage = CityImage;
    }

    public static class CityImageBean {
        /**
         * ImageUrl : http://218.91.209.251:1117/CSYC/2017-09-08/N_2017-09-08 11-00-00_30.78923.jpg
         * ImageDate : 2017-09-08 11:00:00
         */

        private String ImageUrl;
        private String ImageDate;

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getImageDate() {
            return ImageDate;
        }

        public void setImageDate(String ImageDate) {
            this.ImageDate = ImageDate;
        }
    }
}

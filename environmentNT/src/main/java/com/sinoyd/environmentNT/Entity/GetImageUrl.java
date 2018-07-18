package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/7.
 */

public class GetImageUrl {


    private List<JGLDImageBean> JGLDImage;

    public List<JGLDImageBean> getJGLDImage() {
        return JGLDImage;
    }

    public void setJGLDImage(List<JGLDImageBean> JGLDImage) {
        this.JGLDImage = JGLDImage;
    }

    public static class JGLDImageBean {
        /**
         * ImageUrl : http://218.91.209.251:1117/NTJGLDXG/image/chart2017090613-355.svg
         * ImageDate : 2017-09-06 13:00:00
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

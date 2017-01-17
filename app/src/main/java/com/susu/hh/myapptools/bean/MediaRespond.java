package com.susu.hh.myapptools.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MediaRespond {

    /**
     * summary : 动画角色场景神还原
     * movieName : 《美女与野兽》剧场版预告
     * id : 63320
     * movieId : 195064
     * coverImg : http://img5.mtime.cn/mg/2016/11/16/162553.60567933.jpg
     * hightUrl : http://vfx.mtime.cn/Video/2016/11/14/mp4/161114224724894109.mp4
     * videoLength : 120
     * videoTitle : 美女与野兽 剧场版预告片
     * rating : -1
     * type : ["奇幻","歌舞","爱情"]
     * url : http://vfx.mtime.cn/Video/2016/11/14/mp4/161114224724894109_480.mp4
     */

    public List<TrailersBean> trailers;

    public List<TrailersBean> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailersBean> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "MediaRespond{" +
                "trailers=" + trailers +
                '}';
    }

    public static class TrailersBean {
        private String summary;
        private String movieName;
        private int id;
        private int movieId;
        private String coverImg;
        private String hightUrl;
        private int videoLength;
        private String videoTitle;
        private int rating;
        private String url;
        private List<String> type;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "TrailersBean{" +
                    "summary='" + summary + '\'' +
                    ", movieName='" + movieName + '\'' +
                    ", id=" + id +
                    ", movieId=" + movieId +
                    ", coverImg='" + coverImg + '\'' +
                    ", hightUrl='" + hightUrl + '\'' +
                    ", videoLength=" + videoLength +
                    ", videoTitle='" + videoTitle + '\'' +
                    ", rating=" + rating +
                    ", url='" + url + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}

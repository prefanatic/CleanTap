package io.github.prefanatic.cleantap.data.dto;

public class Meta {
    public int code;
    public Time response_time;
    public Time init_time;

    static class Time {
        public float time;
        public String measure;
    }
}

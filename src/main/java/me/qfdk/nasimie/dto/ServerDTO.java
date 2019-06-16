package me.qfdk.nasimie.dto;

import lombok.Data;

@Data
public class ServerDTO {

    private String name;
    private String url;

    public ServerDTO(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ServerDTO{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

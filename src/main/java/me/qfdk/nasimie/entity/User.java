package me.qfdk.nasimie.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String wechatName;

    private Date startTime;

    private Date endTime;

    private boolean isEnable;

    private String containerLocation;

    private String qrCode;

    private String containerId;

    private String containerStatus;

    private String containerPort;

    private String icon;

    private String nickname;

    public User() {

    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        isEnable = enable;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public String getContainerLocation() {
        return containerLocation;
    }

    public void setContainerLocation(String containerLocation) {
        this.containerLocation = containerLocation;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", wechatName='" + wechatName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isEnable=" + isEnable +
                ", containerLocation='" + containerLocation + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", containerId='" + containerId + '\'' +
                ", containerStatus='" + containerStatus + '\'' +
                ", containerPort='" + containerPort + '\'' +
                ", icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

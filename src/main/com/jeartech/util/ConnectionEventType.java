package com.jeartech.util;

public enum ConnectionEventType {
    newConnection("新连接","1001"),
    closeConnection("关闭连接","1002"),
    connection80("192.168.0.80","1"),
    connection247("10.157.195.247","1"),
    connection248("10.157.195.248","2"),
    connection249("10.157.195.249","3"),
    connection250("10.157.195.250","4")
    ;
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    ConnectionEventType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String contains(String ip){
        if(ip.equals(connection80.name))
            return connection80.code;
        if(ip.equals(connection247.name))
            return connection247.code;
        if(ip.equals(connection248.name))
            return  connection248.code;
        if(ip.equals(connection249.name))
            return  connection249.code;
        if(ip.equals(connection250.name))
            return  connection250.code;
        return "none";
    }
}

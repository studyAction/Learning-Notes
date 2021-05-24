package com.yk.create_chapter.connection_pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public abstract class Pool {
    /**
     * 配置文件
     */
    public String propertiesName = "connections.properties";
    /**
     * 唯一实例
     */
    private static Pool instance;

    /**
     * 最大连接数
     */
    protected int maxConnect = 100;

    /**
     * 普通连接数
     */
    protected int normalConnect = 10;
    /**
     * 驱动名
     */
    protected String driverName = null;
    /**
     * 驱动类
     */
    protected Driver driver = null;

    protected Pool() {

    }
    private void init() throws IOException {
        InputStream is = Pool.class.getResourceAsStream(propertiesName);
        Properties p = new Properties();
        p.load(is);
        this.driverName = p.getProperty("driverName");
        this.maxConnect = Integer.parseInt(p.getProperty("maxConnect"));
        this.normalConnect = Integer.parseInt(p.getProperty("normalConnect"));
    }

    protected void loadDrivers(String uri) {
        String driverClassName = uri;
        try {
            driver = (Driver) Class.forName(driverClassName).newInstance();
            DriverManager.registerDriver(driver);
            System.out.println("成功注册JDBC驱动程序：" + driverClassName);
        } catch (Exception ex) {
            System.out.println("无法注册JDBC驱动程序：" + driverClassName + "，错误：" + ex);
        }
    }

    public abstract void createPool();

}

package com.xti.jenkins.plugin.awslambda.upload;

public class DeployConfig {
    private String artifactLocation;
    private String description;
    private String functionName;
    private String handler;
    private Integer memorySize;
    private String role;
    private String runtime;
    private Integer timeout;
    private String updateMode;

    public DeployConfig(String artifactLocation, String description, String functionName, String handler, Integer memorySize, String role, String runtime, Integer timeout, String updateMode) {
        this.artifactLocation = artifactLocation;
        this.description = description;
        this.functionName = functionName;
        this.handler = handler;
        this.memorySize = memorySize;
        this.role = role;
        this.runtime = runtime;
        this.timeout = timeout;
        this.updateMode = updateMode;
    }

    public String getArtifactLocation() {
        return artifactLocation;
    }

    public void setArtifactLocation(String artifactLocation) {
        this.artifactLocation = artifactLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getUpdateMode() {
        return updateMode;
    }

    public void setUpdateMode(String updateMode) {
        this.updateMode = updateMode;
    }
}

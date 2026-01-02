package com.monespaceformation.backend.dto;

public class SessionDto {

    private String id;

    private String title;
    private String reference;
    private String desc;

    private String startDate;
    private String endDate;

    private String duration;
    private String time;

    private Integer price;

    private String category;
    private String level;
    private String location;

    private String trainerName;

    private Integer placesTotales;
    private Integer sessions;

    public SessionDto() {}

    public SessionDto(
            String id,
            String title,
            String reference,
            String desc,
            String startDate,
            String endDate,
            String duration,
            String time,
            Integer price,
            String category,
            String level,
            String location,
            String trainerName,
            Integer placesTotales,
            Integer sessions
    ) {
        this.id = id;
        this.title = title;
        this.reference = reference;
        this.desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.time = time;
        this.price = price;
        this.category = category;
        this.level = level;
        this.location = location;
        this.trainerName = trainerName;
        this.placesTotales = placesTotales;
        this.sessions = sessions;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public Integer getPlacesTotales() { return placesTotales; }
    public void setPlacesTotales(Integer placesTotales) { this.placesTotales = placesTotales; }

    public Integer getSessions() { return sessions; }
    public void setSessions(Integer sessions) { this.sessions = sessions; }
}

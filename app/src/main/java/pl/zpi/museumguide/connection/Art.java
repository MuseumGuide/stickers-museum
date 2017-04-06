package pl.zpi.museumguide.connection;

public class Art
{
    private String name;
    private String summary;

    public Art(String name, String summary) {
        this.name = name;
        this.summary = summary;
    }

    public String toString()
    {
        return name + " " + summary;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }
}

package pl.zpi.museumguide.data.domain;

import java.util.*;

public class Beacon {

    private String uuid;
    //	private String major;
//	private String minor;
//	private String zoneName;
    private List<Work> works;

    public Beacon() {
    }

    public Beacon(String uuid) {
        this.uuid = uuid;
    }

//	public Beacon(String uuid, String major, String minor, String zoneName, Collection<Work> work) {
//		this.uuid = uuid;
//		this.major = major;
//		this.minor = minor;
//		this.zoneName = zoneName;
//		this.work = work;
//	}


    public Beacon(String uuid, List<Work> work) {
        this.uuid = uuid;
        this.works = work;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

//	public String getMajor() {
//		return major;
//	}
//
//	public void setMajor(String major) {
//		this.major = major;
//	}
//
//	public String getMinor() {
//		return minor;
//	}
//
//	public void setMinor(String minor) {
//		this.minor = minor;
//	}
//
//	public String getZoneName() {
//		return zoneName;
//	}
//
//	public void setZoneName(String zoneName) {
//		this.zoneName = zoneName;
//	}

    public List<Work> getWork() {
        return works;
    }

    public void setWork(List<Work> works) {
        this.works = works;
    }

    public void addWork(Work work) {
        if (works == null)
            works = new ArrayList<>();

        works.add(work);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object beacon) {
        if (!(beacon instanceof Beacon))
            return false;

        return this.uuid.equals(((Beacon) beacon).getUuid());
    }
}
package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by fcavasin on 14/05/2018.
 */

class GetReportResponse {
    private int size;
    private List<Report> reports;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}

package me.t.kaurami.service.reportCreator;

import me.t.kaurami.entities.Exportable;

import java.util.LinkedList;
import java.util.List;

public interface ReportCreator {

    public void setData(LinkedList<LinkedList<String>> data);
    public void setLimit(Long limit);
    public List<Exportable> createReport();



}

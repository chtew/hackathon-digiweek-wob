package de.digiweek.service.csvdefinitions;

import com.opencsv.bean.CsvBindByName;

public class InductionLoopRecord {

    @CsvBindByName(column = "Signalanlage")
    public String light;
    
}

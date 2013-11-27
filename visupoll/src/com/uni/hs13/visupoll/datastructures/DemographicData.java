package com.uni.hs13.visupoll.datastructures;

import java.io.Serializable;

public class DemographicData implements Serializable {
	// Values for each row of demographic data
	// Rows: centers, agglo, cities, rural, total
	// Columns: german, french, italian, total
	public float datatable[][] = new float[5][4];
}
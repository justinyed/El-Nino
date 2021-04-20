## El Niño Project

### Background

**El Niño** is a naturally occurring **Southern** climate **Oscillation** (ENSO) event in the equatorial Pacific which returns every 2–7 years. 
- It's responsible for a whole complex of Pacific Ocean sea-surface temperature changes and global weather events. 
- The ocean warming and associated wet climate off South America, which brings disastrous flood and reduces fishing stocks, is just one of these events.

---

### MEI Index

The **Multivariate ENSO Index** (MEI) is a method used to characterize the intensity of an ENSO event using a single number.
- Calculated for each “sliding bi-monthly season”, which are January-February, February-March, and so on for a total of 12 times per year.

|**MEI Time Series**|**Variables**|
|-|-|
|![img](https://projectlovelace.net/static_prod/img/mei_time_series.png)|MEI is composed of Sea level pressure, Zonal and Meridional components of the surface wind, Sea Surface temperature, and Surface Air temperature and Cloudiness.|

---

#### Classifications
- Warm El Niño event occurs when the MEI is at or above +0.5 for five consecutive bi-monthly periods
- Cold La Niña event occurs when the MEI is at or below -0.5 for five consecutive bi-monthly periods. 

**Intensity** — Calculated based on Largest Absolute MEI Value.

| Intensity | MEI Range |
| ------ | ------ |
| Weak | 0.5 to 1.0 |
| Moderate | 1.0 to 1.5 | 
| Strong | 1.5 to 2.0 | 
| Very Strong | ≥ 2.0 | 


---


>  src: <https://projectlovelace.net/problems/el-nino-intensities/>

---

### Process Method

Write a class `ElNino` that implements the following method:

```java
public static Result process(File file, int firstYear)
```

- This method receives a CSV file from where to read Multivariate ENSO Index (MEI) values per bimester per year and the beginning year in a two-year period (biennium) from which to identify whether the period identifies an El Niño or La Niña (or neither) event as well as its intensity and largest value MEI.


**Return Details**

- The method returns a `Result` object holding a <u>classification</u>, <u>intensity</u> and the <u>largest MEI value</u> detected in the event.
  - If both El Niño and La Niña events are detected in the same requested two-year period your method should return the one happening earlier.
  - Else if there are multiple El Niño or La Niña events, then return the one with the highest intensity or MEI value.
  - Else – none are found return event = “Neither”, classification = “none”, and Largest MEI = “0”.
- The method throws an illegal argument exception with message “invalid year: y” where y is the year given as input (firstYear) if it falls outside the range of years given in the data file.
  

**Example File**

#### Return Details

- If a two-year period starting with the `firstYear` is *FOUND*, then the method returns a `Result` object holding a <u>classification</u>, <u>intensity</u> and the <u>largest absolute MEI value</u> detected in the event.
    - If more than one event is found within a two-year period, then *EARLIEST* is returned if different classifications and *LARGEST* MEI value Event is returned if same classifications.
    - Else if no event is found within a two-year period, then return classification = “`Neither`”, intensity = “`none`”, and MEI = “`0`”.
- Else a two-year period starting with the `firstYear` is *NOT FOUND*, then the method throws an illegal argument exception with message “`invalid year: firstYear`”.
  

#### Example File

Tab delimited CSV file:


```
YEAR	DECJAN	JANFEB	FEBMAR	MARAPR	APRMAY	MAYJUN	JUNJUL	JULAUG	AUGSEP	SEPOCT	OCTNOV	NOVDEC
1871	.032	-.055	.005	.420	.195	-.639	-.842	-.474	-.143	.072	.107	-.206
1872	-.633	-.875	-.864	-.664	-.59	-.618	-.658	-.795	-.800	-.669	-.651	-.847
1873	-.93	-.991	-1.301	-1.489	-1.066	-.689	-.657	-.646	-.318	-.012	-.355	-.54
1874	-.611	-.802	-1.036	-1.275	-1.258	-.964	-.902	-1.143	-1.148	-1.003	-.771	-.679
...
```


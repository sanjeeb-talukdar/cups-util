package com.wdpr.conf;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.wdpr.cups.conf.PrinterReader;

/**
 * @author sanjeeb
 */
public class ConfReaderTest {

	@Test
	public void simpleEntry() throws IOException {
		System.out.println(new PrinterReader("D:/CUPS/cups-util/src/test/resources/printers.conf").readObject());

	}
	
	@Test
	public void testSetOp() {
		Set<String> prnMonitored = new HashSet<String>();

		prnMonitored.add("A");
		prnMonitored.add("B");
		prnMonitored.add("C");
		prnMonitored.add("D");

		Set<String> prnCompared = new HashSet<String>();

		prnCompared.add("C");
		prnCompared.add("D");
		prnCompared.add("E");
		prnCompared.add("F");

		final Set<String> opAdd = new HashSet<String>(prnMonitored);
		final Set<String> opUpdate = new HashSet<String>(prnMonitored);
		final Set<String> opDelete = new HashSet<String>(prnCompared);
		opUpdate.retainAll(prnCompared);
		opDelete.removeAll(prnMonitored);
		opAdd.removeAll(prnCompared);

		System.out.println("Updatable :    " + opUpdate);
		System.out.println("Removable :    " + opDelete);
		System.out.println("Addable :    " + opAdd);

	}

}

/*

Description:	HP Universal Printing PCL 6 (v5.6.0)
Location:	Aroli 3rd Floor
Driver:	HP LaserJet M4555 MFP Postscript (recommended) (grayscale, 2-sided printing)
Connection:	ipp://10.74.158.206/ipp
Defaults:	job-sheets=none, none media=na_letter_8.5x11in sides=one-sided

# Printer configuration file for CUPS v1.7.2
# Written by cupsd
# DO NOT EDIT THIS FILE WHEN CUPSD IS RUNNING
<Printer MumbaiSecuredPrint>
UUID urn:uuid:5d7bab02-4717-392a-56fa-4062d14d727a
Info HP Universal Printing PCL 6 (v5.6.0)
Location Aroli 3rd Floor
MakeModel HP LaserJet M4555 MFP Postscript (recommended)
DeviceURI ipp://10.74.158.206/ipp
PPDTimeStamp *
State Idle
StateTime 1421845448
Type 8425684
Accepting Yes
Shared Yes
ColorManaged Yes
JobSheets none none
QuotaPeriod 0
PageLimit 0
KLimit 0
OpPolicy default
ErrorPolicy retry-job
</Printer>
printer=MumbaiSecuredPrint, 
uuid=urn:uuid:5d7bab02-4717-392a-56fa-4062d14d727a, 
info=HP Universal Printing PCL 6 (v5.6.0), 
location=Aroli 3rd Floor, 
makeModel=HP LaserJet M4555 MFP Postscript (recommended), 
deviceURI=ipp://10.74.158.206/ipp, 
pPDTimeStamp=*, 
state=Idle, 
stateTime=1421845448, 
type=8425684, 
accepting=Yes, 
shared=Yes, 
colorManaged=Yes, 
jobSheets=none none, 
quotaPeriod=0, 
pageLimit=0, 
kLimit=0, 
opPolicy=default, 
errorPolicy=retry-job

*/
/*
Description:	HP LaserJet 600 M602
Location:	Aroli 3rd Floor
Driver:	HP LaserJet 600 M601 M602 M603 Postscript (recommended) (grayscale, 2-sided printing)
Connection:	ipp://10.74.158.203/ipp
Defaults:	job-sheets=none, none media=na_letter_8.5x11in sides=one-sided
*/
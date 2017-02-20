package org.cups4j.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.operations.ipp.IppGetPrinterAttributesOperation;

import ch.ethz.vppserver.ippclient.IppResult;
import ch.ethz.vppserver.schema.ippclient.Attribute;
import ch.ethz.vppserver.schema.ippclient.AttributeGroup;
import ch.ethz.vppserver.schema.ippclient.AttributeValue;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class PrinterAttributes {
	private JTabbedPane mainTab = new JTabbedPane();
	private String hostname = "10.74.173.112";
	private String username = "CORP\\satalukd";

	public static void main(String[] args) {
		new PrinterAttributes((args.length > 0) ? args[0] : null);
	}

	/**
	 * @param args
	 */
	public PrinterAttributes(String host) {
		try {
			if (host != null)
				hostname = host;

			JFrame frame = new JFrame("Drucker auf " + hostname);
			frame.setSize(800, 600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(mainTab);
			CupsClient client = new CupsClient(hostname, 631, username);
			// CupsPrinter cupsPrinter = new CupsPrinter(printerURL,
			// printerName, isDefault);
			List<CupsPrinter> printers = client.getPrinters();
			
			System.out.println(printers.size());
			for (CupsPrinter p : printers) {
				IppGetPrinterAttributesOperation o = new IppGetPrinterAttributesOperation();
				IppResult result = o.request(p.getPrinterURL(), null);
				addPrinterPanel(p.getName(), result, p);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addPrinterPanel(String name, IppResult result, CupsPrinter p) {
		mainTab.add(getPrinterPanel(result, p), name);

	}

	private Container getPrinterPanel(IppResult result, CupsPrinter p) {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JTabbedPane tab = new JTabbedPane();
		for (AttributeGroup group : result.getAttributeGroupList()) {
			if (group.getAttribute().size() > 0) {
				tab.add(gatAttributeTab(group, p), group.getTagName());

			}
		}

		jp.add(tab, BorderLayout.CENTER);

		return jp;
	}

	private Component gatAttributeTab(AttributeGroup group, CupsPrinter p) {
		JPanel jp = new JPanel(new BorderLayout());
		ScrollPane scp = new ScrollPane();
		jp.add(scp, BorderLayout.CENTER);

		JButton okButton = new JButton("Print");
		jp.add(okButton, BorderLayout.PAGE_END);
		okButton.addActionListener(new MyActionListener(p));

		FormLayout layout = new FormLayout(
				"12dlu, pref, 6dlu, 30dlu:grow, 3dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setLeadingColumnOffset(1);

		Collections.sort(group.getAttribute(), new Comparator<Attribute>() {

			@Override
			public int compare(Attribute a1, Attribute a2) {
				return a1.getName().compareTo(a2.getName());
			}
		});

		for (Attribute att : group.getAttribute()) {
			JComponent valueComponent = null;
			if (att.getAttributeValue().size() > 0) {
				JPanel panel = new JPanel(new BorderLayout());

				AttributeValueTable table = new AttributeValueTable(
						(getAttributeTableModel(att.getAttributeValue())));
				panel.add(table.getTableHeader(), BorderLayout.NORTH);
				panel.add(table, BorderLayout.CENTER);
				valueComponent = panel;

			} else {
				JLabel lb = new JLabel("no value reported");
				lb.setForeground(Color.red);
				valueComponent = lb;
			}
			builder.appendSeparator();
			builder.append(att.getName(), valueComponent);
			builder.nextLine();
		}
		scp.add(builder.getPanel());

		return jp;
	}

	private class MyActionListener implements ActionListener {
		private CupsPrinter p;

		public MyActionListener(CupsPrinter p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			print(p);
		}

		private void print(CupsPrinter p1) {
			Map<String, String> attributes = new HashMap<String, String>();
			//attributes.put("job-attributes", "print-quality:enum:3#media:keyword:iso_a5_148x210mm");
			PrintJob printJob = new PrintJob.Builder(
					("Hello CUPS4J -- " + p1.getName()).getBytes())
					.jobName("testJobName").userName(username).copies(1)
					.attributes(attributes).build();
			try {
				p1.print(printJob);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private DefaultTableModel getAttributeTableModel(List<AttributeValue> list) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> names = new Vector<String>();
		names.add("Tag Name");
		names.add("Tag (Hex)");
		names.add("Tag Value");
		for (AttributeValue attrValue : list) {
			data.add(getAttributeValue(attrValue));
		}
		return new DefaultTableModel(data, names);

	}

	private Vector<String> getAttributeValue(AttributeValue attrValue) {
		Vector<String> values = new Vector<String>();
		values.add(attrValue.getTagName());
		values.add(attrValue.getTag());
		values.add(attrValue.getValue());
		return values;
	}

	public class AttributeValueTable extends JTable {
		private static final long serialVersionUID = -9079318497719930285L;

		public AttributeValueTable(TableModel model) {
			super(model);
			TableColumnModel colmodel = getColumnModel();
			// Set column widths
			colmodel.getColumn(0).setPreferredWidth(100);
			colmodel.getColumn(1).setPreferredWidth(30);
			colmodel.getColumn(2).setPreferredWidth(150);
		}
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
package utilities;
import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders
{

	// DataProvider 1

	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{

		String path = ".\\testData\\Opencart_LoginData.xlsx"; // taking excel file from testData

		ExcelUtils xlutil = new ExcelUtils(path);  // Creating an object of ExcelUtils

		int totalRows = xlutil.getRowCount("Sheet1");
		int totalColumns = xlutil.getCellCount("Sheet1", 1);

		String loginData[][] = new String[totalRows][totalColumns];  // Created two dimension array which can store login test data

		for(int i=1;i<=totalRows;i++)  // 1  // read the data from xl storing in two dimensional array
		{
			for(int j=0;j<totalColumns;j++)  // 0 // i -> row , j -> column
			{
				loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}

		return loginData;  // return two dimensional array

	}



	// DataProvider 2


	// DataProvider 3

}

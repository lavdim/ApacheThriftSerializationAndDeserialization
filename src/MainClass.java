import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

public class MainClass {

	public static void main(String[] args) throws TException, IOException {
		// TODO Auto-generated method stub

		FileOutputStream fileOutput;

		List<Employee> employeesList = new ArrayList<Employee>();

		long index = 1000000;

		for (int i = 1; i <= index; i++) {

			Employee employee = new Employee();
			employee.setName("Eis" + i);
			employee.setSurnname("IAIS" + i);
			employee.setAge(28);
			employeesList.add(employee);
		}

		double totalTime = 0;
		int repeatedTimes = 11;
		TSerializer serializer = new TSerializer();
		byte[] bytes;

		for (int j = 0; j <= repeatedTimes; j++) {
			fileOutput = new FileOutputStream("PersonsThrift.eis");
			long startTime = System.currentTimeMillis();

			for (int i = 0; i < index; i++) {

				Employee employee = employeesList.get(i);

				bytes = serializer.serialize(employee);

				fileOutput.write(bytes);

			}

			long estimatedTime = System.currentTimeMillis() - startTime;

			if (j > 1) {

				totalTime += estimatedTime;
				System.out.println("Elapsed Time:" + estimatedTime);
			}

		}
		System.out.println("Total:" + totalTime);
		System.out.println("Average:" + totalTime / (repeatedTimes - 1));

	}

}

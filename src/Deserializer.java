import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

public class Deserializer {

	public static void main(String[] args) throws IOException, ClassNotFoundException, TException {
		// TODO Auto-generated method stub

		DeserializeFromList();
		//DeserializeFromFile();

	}

	public static void DeserializeFromList() throws TException {

		TSerializer serializer = new TSerializer();
		byte[] bytes;
		List<byte[]> employeesList = new ArrayList<byte[]>();

		long index = 1000;

		for (int i = 1; i <= index; i++) {

			Employee employee = new Employee();
			employee.setName("Eis" + i);
			employee.setSurnname("IAIS" + i);
			employee.setAge(28);
			bytes = serializer.serialize(employee);
			employeesList.add(bytes);

		}

		double totalTime = 0;
		int repeatedTimes = 11;
		TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
		Employee employee;

		for (int j = 0; j <= repeatedTimes; j++) {

			long startTime = System.currentTimeMillis();

			for (int i = 0; i < index; i++) {

				employee = new Employee();
				byte[] byteEmployee = employeesList.get(i);

				deserializer.deserialize(employee, byteEmployee);
				// System.out.println(employee.getName());
			}

			long estimatedTime = System.currentTimeMillis() - startTime;

			if (j > 1) {
				totalTime += estimatedTime;
				System.out.println("Elapsed Time:" + estimatedTime);
			}

		}
		System.out.println("Total:" + totalTime);
		System.out.println("Average:" + totalTime / (repeatedTimes-1));

	}

	@SuppressWarnings("resource")
	public static void DeserializeFromFile() throws IOException, TException {

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
		FileOutputStream fileOutput;

		fileOutput = new FileOutputStream("PersonsThrift.eis");

		for (int i = 0; i < index; i++) {

			Employee employee = employeesList.get(i);

			bytes = serializer.serialize(employee);

			fileOutput.write(bytes);
		}

		

		for (int j = 0; j <= repeatedTimes; j++) {
			
			ThriftReader thriftIn = new ThriftReader(new File("PersonsThrift.eis"), new ThriftReader.TBaseCreator() {
				@Override
				public TBase create() {
					return new Employee();
				}
			});

			thriftIn.open();
			
			List<Employee> employees = new ArrayList<Employee>();

			long startTime = System.currentTimeMillis();
			
			while (thriftIn.hasNext()) {
				Employee employee = (Employee) thriftIn.read();

				//System.out.println(employee.getName());
				employees.add(employee);

			}
			long estimatedTime = System.currentTimeMillis() - startTime;

			if (j > 1) {
				totalTime += estimatedTime;
				System.out.println("Elapsed Time:" + estimatedTime);
			}
			thriftIn.close();
		}
		System.out.println("Total:" + totalTime);
		System.out.println("Average:" + totalTime / (repeatedTimes-1));

		// Close reader
		
	}

}

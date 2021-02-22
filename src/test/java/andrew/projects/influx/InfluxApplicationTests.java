package andrew.projects.influx;

import andrew.projects.influx.Domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class InfluxApplicationTests {

	@Test
	void contextLoads() {

	}
	@Test
	public void testRoleComparison(){
		/*List<Role> roles = new ArrayList<>();
		for(int i=0; i<10;i++){
			roles.add(new Role(""+i));
		}
		System.out.println(roles.contains(new Role("Owner")));
		roles.add(new Role("Owner"));
		System.out.println(roles.contains(new Role("Owner")));*/
	}

}

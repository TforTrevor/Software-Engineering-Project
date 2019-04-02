package Tests;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {
    @BeforeClass
    {

    }
    @Test(timeout = 1000)
    public void fileReaderTest(){
        FileReader testing=new FileReader("01/09/2019","01/11/2019","C:\\Users\\Jpc\\Documents\\NetBeansProjects");
        testing.SearchImages();
        ArrayList<String> output = testing.GetImages();
    }
}

//make a file
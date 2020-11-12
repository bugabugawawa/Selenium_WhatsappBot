import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class bot {
	public static void main(String[] args) throws InterruptedException{
		String mensagemBV = "Boas vindas! eu sou o jarbas seu bot assistente,\npara testar-me digite '&' seguido de uma mensagem e eu irei repeti-la(com sorte apenas uma vez)";
		String add = "adicionou";
		String check_add = "";
		String mensagem = "";
		String nome = "";
		String data = "";
		String ultima_mensagem = "";
		
		int prioridade;
		int count = 1;
		int tamanho;
		
		WebElement nome_ultimo;
		List<WebElement> nome_trigger;
		WebElement triggered_mensagem_ultima;
		List<WebElement> triggered_mensagem;
		String triggered_mensagem_cortada;
		WebElement trigger;
		List<WebElement> triggered_join;
		
		System.setProperty("webdriver.chrome.driver", "./src/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://web.whatsapp.com");
		TimeUnit.SECONDS.sleep(1);
		WebElement grupo = driver.findElement(By.xpath("//span[@title='teste']"));
		grupo.click();
				
		while(true) {			
			triggered_mensagem = driver.findElements(By.className("message-in"));
			triggered_mensagem_ultima = triggered_mensagem.get(triggered_mensagem.size()-count);
			if(triggered_mensagem_ultima.getText().equals(ultima_mensagem)) {
				TimeUnit.SECONDS.sleep(1);
				continue;
			}
			ultima_mensagem = triggered_mensagem_ultima.getText();
			tamanho = triggered_mensagem_ultima.findElement(By.className("Tkt2p")).getText().split("\n").length;
			nome = driver.findElement(By.className("_1OmDL")).getText();
			mensagem = triggered_mensagem_ultima.findElement(By.className("copyable-text")).getText();
			prioridade = mensagem.charAt(0);
			if(prioridade!=42) {prioridade=1;}
			data = triggered_mensagem_ultima.findElement(By.className("Tkt2p")).getText().split("\n")[tamanho - 1];
            if(Mysql.id(nome, data, mensagem)!=1){
            	count++;
                Mysql.registro(mensagem, nome, data, prioridade);
                mensagem = "Recebi sua atualização!";
                EnviarMensagem(driver, mensagem);
                }
            else if(Mysql.id(nome, data, mensagem)==1){count=1;}

            /**triggered_join = driver.findElements(By.className("_2ArBI"));
            triggered_mensagem_ultima = triggered_join.get(triggered_join.size()-1);
			triggered_mensagem_cortada = triggered_mensagem_ultima.getText().split(" ");
            if(triggered_mensagem_cortada[1] == add && check_add != triggered_mensagem_ultima.getText()) {
                check_add = triggered_mensagem_ultima.getText();
                EnviarMensagem(driver, mensagemBV);
            	}*/
			}
		}
	
    public static void EnviarMensagem(WebDriver driver, String mensagem) throws InterruptedException {
        WebElement chatbox = driver.findElement(By.className("_1Plpp"));
        TimeUnit.SECONDS.sleep(1);
        chatbox.click();
        chatbox.sendKeys(mensagem);
        WebElement enviar = driver.findElement(By.xpath("//span[@data-icon='send']"));
        TimeUnit.SECONDS.sleep(1);
        enviar.click();
    }
}

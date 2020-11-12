import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class bot {
	public static void main(String[] args) throws InterruptedException{
		String mensagem = "";
		String nome = "";
		String data = "";
		String ultima_mensagem = "";
		String num="";
		String num_comp = "+55";
		String test;
		
		Boolean achou=true;
		
		int prioridade;
		int count = 1;
		int tamanho;
		int mini_count=1;
		
		WebElement triggered_mensagem_ultima;
		List<WebElement> triggered_mensagem;
		System.setProperty("webdriver.chrome.driver", "./src/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://web.whatsapp.com");
		TimeUnit.SECONDS.sleep(3);
		WebElement grupo = driver.findElement(By.xpath("//span[@title='Teste painel Vobys']"));
		TimeUnit.SECONDS.sleep(3);
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
			num = ultima_mensagem.split("\n")[0];
			
			if(!num.split(" ")[0].equals(num_comp)) {
				mini_count=count;
				while(achou) {
					test = triggered_mensagem.get(triggered_mensagem.size()-mini_count).getText();
					if(test.split(" ")[0].equals(num_comp)) {
						mini_count=1;
						num = test.split("\n")[0];
						achou=false;
						nome = Mysql.nome(num);
						break;
						}
					mini_count++;
				}
			}
			
			achou = true;
			mensagem = triggered_mensagem_ultima.findElement(By.className("copyable-text")).getText();
			prioridade = mensagem.charAt(0);
			if(prioridade!=42) {prioridade=1;}
			data = triggered_mensagem_ultima.findElement(By.className("Tkt2p")).getText().split("\n")[tamanho - 1];
            if(Mysql.id(data, mensagem, num)!=1){
            	if(triggered_mensagem.size()!=count) count++;
                Mysql.registro(nome, mensagem, data, prioridade, num);
                mensagem = "Recebi sua atualização!";
                EnviarMensagem(driver, mensagem);
                }
            else if(Mysql.id(data, mensagem, num)==1){count=1;}
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

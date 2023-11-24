package controller;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;
import model.UsuarioDao;

public class JavaMail {
    
    public JavaMail () {
 
    }
        
    public int enviaCodRecuperacao (String emailDest) {
        int codRecuperacao;
        
        UsuarioDao usrDao = new UsuarioDao();
        if (!usrDao.validaEmail(emailDest)) {
            codRecuperacao = -1;
            return codRecuperacao;
        } 
        
        String SENHA_APP = "xzyh nnxn oock sgmu";
        String EMAIL_RECUP = "dominusapp2023@gmail.com";
        
        Random random = new Random();
        codRecuperacao = random.nextInt(1000, 9999);
        String mensagem = "Olá! Aqui está o seu código de recuperação de senha: " + codRecuperacao;
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.enable", "true");
        
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_RECUP, SENHA_APP);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_RECUP));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDest));
            message.setSubject("Recuperação de senha DominusApp");
            message.setText(mensagem);
            
            Transport.send(message);
            System.out.println("Email de recuperação enviado com sucesso.");
            
        } catch (MessagingException e) {
            e.printStackTrace();
            codRecuperacao = -1;
        }
        return codRecuperacao;
    }
    
    
   
    
}

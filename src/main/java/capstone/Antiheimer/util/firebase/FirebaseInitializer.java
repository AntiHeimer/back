//package capstone.Antiheimer.firebase;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Service
//public class FirebaseInitializer {
//
//    @PostConstruct
//    public void initialize() {
//        try {
//            ClassPathResource resource = new ClassPathResource("firebase/antiheimer-27faa-firebase-adminsdk-fp8cv-7c98759331.json");
//            InputStream serviceAccount = resource.getInputStream();
//
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
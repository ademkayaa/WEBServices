package com.server.approve;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.Endpoint;
import java.util.Arrays;
import java.util.List;

@WebService()
public class ApproveService {
    String userApp, relativeId;
    Integer jobId;

    @WebMethod()
    public String updateUserApprove(@XmlElement(required = true, nillable = false) requestApprove ra){

        //guiden gelen degerler.
        userApp = ra.getUserApprove();
        jobId = ra.getJobId();
        relativeId = ra.getRelativeId().toString();
        //guiden gelen jobid ile db deki job bilgilerine ulasiyoruz.
//Job job = GetJob(jobId); //???

        //o jobun relativeleri
//String relatives = job.getRelatives(); ????

        String relatives = "123 | 342 | 231";

        //bize gelen relativeId nin hangi sırada oldugunu buluyoruz.
        //db den "x|x|x" şeklinde string geliyor ve bize gelen relatiiveId hangi sırada onu buluyoruz.
        //Cunku db deki rule tablosunda ki relativeStatus "x,x,x"  stringini buldugumuz indexi degistiyoruz.
        //Cevap ise guiden gelen userApprove

        //Bize gelen stringi listeye atip indexini buldum.
        // | ve bosluklari yok sayarak
        List<String> reList = Arrays.asList(relatives.split("\\s*\\|\\s*"));
        //try catch eklenecek
        int index = reList.indexOf(relativeId);
        //job tablosundan ruleid ye ulasip oradan rule tablosuna erisiyoruz.
//int ruleId = job.getRuleId(); //?????
//Rules rule = getRule(ruleId); //?????

        //Relativeresult i , ve bosluklari yok sayarak listeye attim.
        //yukarida buldugum indexteki yere userApprove ile degistirdim listede.
//String relRes = rule.getRelativeResults();????
        String relativeRes = "X,X,X";
        List<String> resList = Arrays.asList(relativeRes.split("\\s*,\\s*"));
        resList.set(index,userApp);


        //listeyi tekrar stringe cevirdim virgulle ayrilmis sekilde.
        StringBuilder strBuilder = new StringBuilder();
        for(int i = 0; i < resList.size();i++){
            if(i == resList.size() -1){
                strBuilder.append(resList.get(i));
            }
            else{
                strBuilder.append(resList.get(i) + ",");
            }
        }
        String newRelative = strBuilder.toString();

        //En son olarak rule tablosunu guncelledim.
//rule.UpdateRule(ruleId,"RelativeResults",newRelative); ????
        System.out.println(newRelative);

        return "update relative";
    }

    public static void main(String argv[]){
        Object implementor = new ApproveService();
        String address = "http://localhost:8080/Approve";
        Endpoint.publish(address, implementor);

    }
}
package top.tosim.actrainer.service;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tosim.actrainer.dto.ExcelDto;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.tool.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Transactional
public class ExcelService {
    @Autowired
    ContestService contestService;

    public RespJson downloadContestRank(HttpServletResponse response, Integer contestId){
        RespJson respJson = new RespJson();
        String fileName = "Contest_"+contestId+System.currentTimeMillis()+".xls"; //文件名
        String sheetName = "Contest_"+contestId;//sheet名

        RespJson originData = contestService.getContestRank(contestId);
        Map<String,Object> obj = (Map<String, Object>)originData.getObj();
        Map<Integer,String> parts = (Map<Integer, String>) obj.get("parts");
        List<List> submit = (List<List>)obj.get("submit");
        Long length = (Long)obj.get("length");
        Integer number = (Integer)obj.get("number");

        ExcelDto excelDto = Calculate(parts,submit,length,number);
//        System.out.println(JSON.toJSONString(excelDto));
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, excelDto.getTitle(), excelDto.getValues(), null);

        //将文件存到指定位置
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            respJson.setSuccess(0);
            respJson.setMsg("genarate excel error");
        }
        respJson.setSuccess(1);
        return respJson;
    }

    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private ExcelDto Calculate(Map<Integer,String> parts, List<List> submit, Long length, Integer number){
        class UserSub{
            public String accountName = "";
            public Integer acCount = 0;
            public Integer penalty = 0;
            public List<List<Integer>> problems = new ArrayList<>();
        }
        Map<Integer,UserSub> map = new HashMap<Integer, UserSub>();
        for(Map.Entry<Integer,String> entry : parts.entrySet()){
            int id = entry.getKey();
            map.put(entry.getKey(),new UserSub());
            for(int i = 0;i < number;i++){
                List<Integer> problem = new ArrayList<Integer>();
                problem.add(-1);
                problem.add(0);
                map.get(id).problems.add(problem);
            }
        }
        for(int i = 0;i < submit.size();i++){
            Integer uid = (Integer)submit.get(i).get(0);
            Integer index = (Integer)submit.get(i).get(1);
            Integer isAC = (Integer)submit.get(i).get(2);
            Long acTime = (Long)submit.get(i).get(3);
            if(acTime.longValue() > length.longValue()){
                continue;
            }

            if(map.get(uid).problems.get(index) != null && !map.get(uid).problems.get(index).get(0).equals(-1)){
                continue;//如果已经AC，time已经有值，提交无效
            }
            if(isAC == 1){
                map.get(uid).problems.get(index).set(0,acTime.intValue());
                map.get(uid).acCount++;
                map.get(uid).penalty += (acTime.intValue() + map.get(uid).problems.get(index).get(1)*20*60*1000);
            }else{
                map.get(uid).problems.get(index).set(1,map.get(uid).problems.get(index).get(1)+1);
            }
        }

        List<UserSub> rankArr = new ArrayList<UserSub>();
        for(Map.Entry<Integer,UserSub> entry : map.entrySet()){
            Integer id = entry.getKey();
            map.get(id).accountName = parts.get(id);
            rankArr.add(map.get(id));
        }
        rankArr.sort(new Comparator<UserSub>() {
            @Override
            public int compare(UserSub a, UserSub b) {
                if(a.acCount > b.acCount){
                    return -1;
                }else if(a.acCount == b.acCount){
                    if(a.penalty < b.penalty){
                        return -1;
                    }else{
                        return 1;
                    }
                }else{
                    return 1;
                }
            }
        });
        List<String> title = new ArrayList<String>(Arrays.asList("Rank","Team","Score","Penalty"));
        List<List<String>> values = new ArrayList<>();
        for(int i = 0;i < number;i++){
            title.add(String.valueOf((char)(65+i)));
        }
        for(int i = 0;i < rankArr.size();i++){
            List<String> row = new ArrayList<String>();
            row.add((i+1)+"");
            row.add(rankArr.get(i).accountName);
            row.add(rankArr.get(i).acCount+"");
            row.add(rankArr.get(i).penalty/1000/60+"");
            for(int j = 0;j < number;j++){
                Integer l = rankArr.get(i).problems.get(j).get(0);
                String res = "";
                if(!l.equals(-1)){
                    Integer h = (l / (1000 * 60 * 60));
                    Integer m = (l % (1000 * 60 * 60)) / (1000 * 60);
                    Integer s = (l % (1000 * 60)) / 1000;
                    res = res + h+":"+m+":"+s;
                }
                if(!rankArr.get(i).problems.get(j).get(1).equals(0)){
                    res = res + "(-"+rankArr.get(i).problems.get(j).get(1)+")";
                }
                row.add(res);
            }
            values.add(row);
        }
        ExcelDto excelDto = new ExcelDto();
        excelDto.setTitle(title);
        excelDto.setValues(values);
        return excelDto;
    }
}

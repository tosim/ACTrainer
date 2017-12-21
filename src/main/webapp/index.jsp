<html>
<body>
<script src=”http://libs.baidu.com/jquery/2.0.0/jquery.min.js”></script>
<script>
//根据参与者和提交记录计算排名
function calculateRank(parts,submit,length){
    // var parts = {
    //     327:['a','aaa'],
    //     515:['b','bbb'],
    //     155:['c','ccc']
    // }
    // var submit = [
    //     [327,0,0,12],
    //     [515,0,1,12],
    //     [327,0,1,20],
    //     [327,0,1,33],
    //     [327,1,1,33],
    //     [155,0,1,66],
    //     [155,1,1,81981],
    //     [155,2,1,198190]
    // ];
    var map = {};

    for(let i = 0;i < submit.length;i++){
        var id = submit[i][0];
        var que = submit[i][1];
        var is_AC = submit[i][2];
        var time = submit[i][3];
        if(time > length){
            continue;
        }
        try{
            if(map[id] == null){
                map[id] = {};
                map[id].isSolve = {};
                map[id].totalTime = {};
                map[id].time = 0;
                if(is_AC == 1){
                    map[id].solveCnt = 1;
                    map[id].totalTime[que] = time;
                    map[id].isSolve[que] = 1;
                    map[id].time += map[id].totalTime[que];
                }else{
                    map[id].solveCnt = 0;
                    map[id].isSolve[que] = 0;
                    map[id].totalTime[que] = 1200;
                }
            }else{
                if(is_AC == 1){
                    if(map[id].isSolve[que] == null){
                        map[id].totalTime[que] = time;
                        map[id].solveCnt++;
                    }else if(map[id].isSolve[que] == 0){
                        map[id].totalTime[que] += time;
                        map[id].solveCnt++;
                    }
                    map[id].isSolve[que] = 1;
                    map[id].time += map[id].totalTime[que];
                }else{
                    if(map[id].isSolve[que] == null){
                        map[id].totalTime[que] = 1200;
                    }else if(map[id].isSolve[que] == 0){
                        map[id].totalTime[que] += 1200;
                    }
                    map[id].isSolve[que] = 0;
                }
            }}catch(e){
            console.log(e);
            throw(e);
        }
    }
    var arr = [];
    for(let i in map){
        map[i].id = i;
        arr.push(map[i]);
    }
    arr.sort(function(a,b){
        if(a.solveCnt > b.solveCnt){
            return -1;
        }else if(a.solveCnt == b.solveCnt){
            if(a.time < b.time){
                return -1
            }else{
                return 1;
            }
        }else{
            return 1;
        }
    });
    for(let i = 0;i < arr.length;i++){
        arr[i].nickName = parts[arr[i].id][0];
        arr[i].name = parts[arr[i].id][1]
    }
    return arr;
}
</script>
<h2>Hello World!</h2>
<script>
    $(function(){
        $.ajax({
            url:"/contests/3/rank",
            success:function(data){
                console.log(data);
            }
        })
    })
</script>
</body>
</html>

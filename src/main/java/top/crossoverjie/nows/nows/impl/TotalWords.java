package top.crossoverjie.nows.nows.impl;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 19:48
 * @since JDK 1.8
 */

@Component
public class TotalWords {

    private AtomicLong sum = new AtomicLong() ;

    //private long sum = 0 ;

    public void sum(int count){
        sum.addAndGet(count) ;
    }

    public  long total(){
        return sum.get() ;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.namsor.oss.samples;

/**
 *
 * @author elian
 */
public class Main {

    public static final void main(String[] args) {
// laplaced smoothing alpha 
        double alpha = 1.0;

// laplaced smoothing variant : false
// Features : 
// Feature 	temp	Cool
// Feature 	humidity	High
// Feature 	outlook	Overcast
// Feature 	wind	Strong
// Features (safe) : 
// Feature 	temp	Cool
// Feature 	humidity	High
// Feature 	outlook	Overcast
// Feature 	wind	Strong
// observation table variables 
        double gL = 14;
        double gL_cA_No = 5;
        double gL_cA_No_fE_humidity = 5;
        double gL_cA_No_fE_humidity_is_High = 4;
        double gL_cA_No_fE_outlook = 5;
        double gL_cA_No_fE_outlook_is_Overcast = 0;
        double gL_cA_No_fE_temp = 5;
        double gL_cA_No_fE_temp_is_Cool = 1;
        double gL_cA_No_fE_wind = 5;
        double gL_cA_No_fE_wind_is_Strong = 3;
        double gL_cA_Yes = 9;
        double gL_cA_Yes_fE_humidity = 9;
        double gL_cA_Yes_fE_humidity_is_High = 3;
        double gL_cA_Yes_fE_outlook = 9;
        double gL_cA_Yes_fE_outlook_is_Overcast = 4;
        double gL_cA_Yes_fE_temp = 9;
        double gL_cA_Yes_fE_temp_is_Cool = 3;
        double gL_cA_Yes_fE_wind = 9;
        double gL_cA_Yes_fE_wind_is_Strong = 3;
        double gL_count = 2;
        double gL_fE_humidity = 14;
        double gL_fE_humidity_count = 2;
        double gL_fE_outlook = 14;
        double gL_fE_outlook_count = 3;
        double gL_fE_temp = 14;
        double gL_fE_temp_count = 3;
        double gL_fE_wind = 14;
        double gL_fE_wind_count = 2;                // likelyhoods for category Yes

        double likelyhoodOfYes = Math.exp((Math.log(gL_cA_Yes + alpha) - Math.log(gL + (gL_count * alpha))) + (Math.log(gL_cA_Yes_fE_temp_is_Cool + alpha) - Math.log(gL_cA_Yes_fE_temp + (gL_fE_temp_count * alpha)) + Math.log(gL_cA_Yes_fE_humidity_is_High + alpha) - Math.log(gL_cA_Yes_fE_humidity + (gL_fE_humidity_count * alpha)) + Math.log(gL_cA_Yes_fE_outlook_is_Overcast + alpha) - Math.log(gL_cA_Yes_fE_outlook + (gL_fE_outlook_count * alpha)) + Math.log(gL_cA_Yes_fE_wind_is_Strong + alpha) - Math.log(gL_cA_Yes_fE_wind + (gL_fE_wind_count * alpha)) + 0));
        double likelyhoodOfYesExpr = Math.exp((Math.log(9 + 1.0) - Math.log(14 + (2 * 1.0))) + (Math.log(3 + 1.0) - Math.log(9 + (3 * 1.0)) + Math.log(3 + 1.0) - Math.log(9 + (2 * 1.0)) + Math.log(4 + 1.0) - Math.log(9 + (3 * 1.0)) + Math.log(3 + 1.0) - Math.log(9 + (2 * 1.0)) + 0));
        double likelyhoodOfYesValue = 0.011478420569329652;
        print(likelyhoodOfYes);
        print(likelyhoodOfYesExpr);
        print(likelyhoodOfYesValue); 
        // basicProbabilities (compared to best alternative) : 
        // p temp=Cool : -1.099 vs -1.386 factor=NaN
        // p humidity=High : -1.012 vs -0.336 factor=NaN
        // p outlook=Overcast : -0.875 vs -2.079 factor=NaN
        // p wind=Strong : -1.012 vs -0.56 factor=NaN
        // basicProbabilities : 
        // p temp=Cool : -1.0986122886681098
        // p humidity=High : -1.01160091167848
        // p outlook=Overcast : -0.8754687373539001
        // p wind=Strong : -1.01160091167848
        // likelyhoods for category No

        double likelyhoodOfNo = Math.exp((Math.log(gL_cA_No + alpha) - Math.log(gL + (gL_count * alpha))) + (Math.log(gL_cA_No_fE_temp_is_Cool + alpha) - Math.log(gL_cA_No_fE_temp + (gL_fE_temp_count * alpha)) + Math.log(gL_cA_No_fE_humidity_is_High + alpha) - Math.log(gL_cA_No_fE_humidity + (gL_fE_humidity_count * alpha)) + Math.log(gL_cA_No_fE_outlook_is_Overcast + alpha) - Math.log(gL_cA_No_fE_outlook + (gL_fE_outlook_count * alpha)) + Math.log(gL_cA_No_fE_wind_is_Strong + alpha) - Math.log(gL_cA_No_fE_wind + (gL_fE_wind_count * alpha)) + 0));
        double likelyhoodOfNoExpr = Math.exp((Math.log(5 + 1.0) - Math.log(14 + (2 * 1.0))) + (Math.log(1 + 1.0) - Math.log(5 + (3 * 1.0)) + Math.log(4 + 1.0) - Math.log(5 + (2 * 1.0)) + Math.log(0 + 1.0) - Math.log(5 + (3 * 1.0)) + Math.log(3 + 1.0) - Math.log(5 + (2 * 1.0)) + 0));
        double likelyhoodOfNoValue = 0.004783163265306125;
        print(likelyhoodOfNo);
        print(likelyhoodOfNoExpr);
        print(likelyhoodOfNoValue);
        // basicProbabilities : 
        // p temp=Cool : -1.3862943611198904
        // p humidity=High : -0.33647223662121295
        // p outlook=Overcast : -2.0794415416798357
        // p wind=Strong : -0.5596157879354227
        // probability estimates by category 
        // probability estimate for category Yes

        double probabilityOfYes = likelyhoodOfYes / (likelyhoodOfYes + likelyhoodOfNo + 0);
        double probabilityOfYesValue = 0.7215830648872524;
        // probability estimate for category No

        double probabilityOfNo = likelyhoodOfNo / (likelyhoodOfYes + likelyhoodOfNo + 0);
        double probabilityOfNoValue = 0.27841693511274745;
        // return the highest probability estimate for evaluation 

        print(probabilityOfYes);
        print(probabilityOfYesValue);
    }

    private static void print(double v) {
        System.out.println(v);
    }
}

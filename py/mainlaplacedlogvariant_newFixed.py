#Python script below # Python : 

# laplaced smoothing alpha 
alpha=1.0

# laplaced smoothing variant : true

# log product variant : true

import math

# Features : 
# Feature 	temp	Cool
# Feature 	humidity	High
# Feature 	outlook	Overcast
# Feature 	wind	Strong

# Features (safe) : 
# Feature 	temp	Cool
# Feature 	humidity	High
# Feature 	outlook	Overcast
# Feature 	wind	Strong

# observation table variables 
gL=14
gL_cA_No=5
gL_cA_No_fE_humidity=5
gL_cA_No_fE_humidity_is_High=4
gL_cA_No_fE_outlook=5
gL_cA_No_fE_outlook_is_Overcast=0
gL_cA_No_fE_temp=5
gL_cA_No_fE_temp_is_Cool=1
gL_cA_No_fE_wind=5
gL_cA_No_fE_wind_is_Strong=3
gL_cA_Yes=9
gL_cA_Yes_fE_humidity=9
gL_cA_Yes_fE_humidity_is_High=3
gL_cA_Yes_fE_outlook=9
gL_cA_Yes_fE_outlook_is_Overcast=4
gL_cA_Yes_fE_temp=9
gL_cA_Yes_fE_temp_is_Cool=3
gL_cA_Yes_fE_wind=9
gL_cA_Yes_fE_wind_is_Strong=3
gL_count=2
gL_fE_humidity=14
gL_fE_humidity_count=2
gL_fE_outlook=14
gL_fE_outlook_count=3
gL_fE_temp=14
gL_fE_temp_count=3
gL_fE_wind=14
gL_fE_wind_count=2


# likelyhoods by category 

# likelyhoods for category Yes
likelyhoodOfYes=math.exp((math.log(gL_cA_Yes + alpha) - math.log(gL + (gL_count * alpha))) + (math.log(gL_cA_Yes_fE_temp_is_Cool + alpha)-math.log(gL_cA_Yes_fE_temp + ( gL_fE_temp_count * alpha )) + math.log(gL_cA_Yes_fE_humidity_is_High + alpha)-math.log(gL_cA_Yes_fE_humidity + ( gL_fE_humidity_count * alpha )) + math.log(gL_cA_Yes_fE_outlook_is_Overcast + alpha)-math.log(gL_cA_Yes_fE_outlook + ( gL_fE_outlook_count * alpha )) + math.log(gL_cA_Yes_fE_wind_is_Strong + alpha)-math.log(gL_cA_Yes_fE_wind + ( gL_fE_wind_count * alpha )) + 0 ))
likelyhoodOfYesExpr=math.exp((math.log(9 + 1.0) - math.log(14 + (2 * 1.0))) + (math.log(3 + 1.0 )-math.log(9 + ( 3 * 1.0 )) + math.log(3 + 1.0 )-math.log(9 + ( 2 * 1.0 )) + math.log(4 + 1.0 )-math.log(9 + ( 3 * 1.0 )) + math.log(3 + 1.0 )-math.log(9 + ( 2 * 1.0 )) + 0 ))
likelyhoodOfYesValue=0.011478420569329652
# basicProbabilities (compared to best alternative) : 
# p temp=Cool : -1.099 vs -1.386 factor=NaN
# p humidity=High : -1.012 vs -0.336 factor=NaN
# p outlook=Overcast : -0.875 vs -2.079 factor=NaN
# p wind=Strong : -1.012 vs -0.56 factor=NaN
# basicProbabilities : 
# p temp=Cool : -1.0986122886681098
# p humidity=High : -1.01160091167848
# p outlook=Overcast : -0.8754687373539001
# p wind=Strong : -1.01160091167848

# likelyhoods for category No
likelyhoodOfNo=math.exp((math.log(gL_cA_No + alpha) - math.log(gL + (gL_count * alpha))) + (math.log(gL_cA_No_fE_temp_is_Cool + alpha)-math.log(gL_cA_No_fE_temp + ( gL_fE_temp_count * alpha )) + math.log(gL_cA_No_fE_humidity_is_High + alpha)-math.log(gL_cA_No_fE_humidity + ( gL_fE_humidity_count * alpha )) + math.log(gL_cA_No_fE_outlook_is_Overcast + alpha)-math.log(gL_cA_No_fE_outlook + ( gL_fE_outlook_count * alpha )) + math.log(gL_cA_No_fE_wind_is_Strong + alpha)-math.log(gL_cA_No_fE_wind + ( gL_fE_wind_count * alpha )) + 0 ))
likelyhoodOfNoExpr=math.exp((math.log(5 + 1.0) - math.log(14 + (2 * 1.0))) + (math.log(1 + 1.0 )-math.log(5 + ( 3 * 1.0 )) + math.log(4 + 1.0 )-math.log(5 + ( 2 * 1.0 )) + math.log(0 + 1.0 )-math.log(5 + ( 3 * 1.0 )) + math.log(3 + 1.0 )-math.log(5 + ( 2 * 1.0 )) + 0 ))
likelyhoodOfNoValue=0.004783163265306125
# basicProbabilities : 
# p temp=Cool : -1.3862943611198904
# p humidity=High : -0.33647223662121295
# p outlook=Overcast : -2.0794415416798357
# p wind=Strong : -0.5596157879354227


# probability estimates by category 

# probability estimate for category Yes
probabilityOfYes=likelyhoodOfYes/(likelyhoodOfYes+likelyhoodOfNo+0)
probabilityOfYesValue=0.7058611686323938

# probability estimate for category No
probabilityOfNo=likelyhoodOfNo/(likelyhoodOfYes+likelyhoodOfNo+0)
probabilityOfNoValue=0.29413883136760627


# return the highest probability estimate for evaluation 
print(probabilityOfYes)

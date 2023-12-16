#Python script below # Python : 

# laplaced smoothing alpha 
alpha=1.0

# laplaced smoothing variant : false

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
likelyhoodOfYes=gL_cA_Yes / gL * ((gL_cA_Yes_fE_temp_is_Cool + alpha)/(gL_cA_Yes_fE_temp + ( gL_fE_temp_count * alpha )) * (gL_cA_Yes_fE_humidity_is_High + alpha)/(gL_cA_Yes_fE_humidity + ( gL_fE_humidity_count * alpha )) * (gL_cA_Yes_fE_outlook_is_Overcast + alpha)/(gL_cA_Yes_fE_outlook + ( gL_fE_outlook_count * alpha )) * (gL_cA_Yes_fE_wind_is_Strong + alpha)/(gL_cA_Yes_fE_wind + ( gL_fE_wind_count * alpha )) * 1 )
likelyhoodOfYesExpr=9 / 14 * ((3 + 1.0 )/(9 + ( 3 * 1.0 )) * (3 + 1.0 )/(9 + ( 2 * 1.0 )) * (4 + 1.0 )/(9 + ( 3 * 1.0 )) * (3 + 1.0 )/(9 + ( 2 * 1.0 )) * 1 )
likelyhoodOfYesValue=0.011806375442739082
# basicProbabilities (compared to best alternative) : 
# p temp=Cool : 0.333 vs 0.25 factor=1.333
# p humidity=High : 0.364 vs 0.714 factor=0.509
# p outlook=Overcast : 0.417 vs 0.125 factor=3.333
# p wind=Strong : 0.364 vs 0.571 factor=0.636
# basicProbabilities : 
# p temp=Cool : 0.3333333333333333
# p humidity=High : 0.36363636363636365
# p outlook=Overcast : 0.4166666666666667
# p wind=Strong : 0.36363636363636365

# likelyhoods for category No
likelyhoodOfNo=gL_cA_No / gL * ((gL_cA_No_fE_temp_is_Cool + alpha)/(gL_cA_No_fE_temp + ( gL_fE_temp_count * alpha )) * (gL_cA_No_fE_humidity_is_High + alpha)/(gL_cA_No_fE_humidity + ( gL_fE_humidity_count * alpha )) * (gL_cA_No_fE_outlook_is_Overcast + alpha)/(gL_cA_No_fE_outlook + ( gL_fE_outlook_count * alpha )) * (gL_cA_No_fE_wind_is_Strong + alpha)/(gL_cA_No_fE_wind + ( gL_fE_wind_count * alpha )) * 1 )
likelyhoodOfNoExpr=5 / 14 * ((1 + 1.0 )/(5 + ( 3 * 1.0 )) * (4 + 1.0 )/(5 + ( 2 * 1.0 )) * (0 + 1.0 )/(5 + ( 3 * 1.0 )) * (3 + 1.0 )/(5 + ( 2 * 1.0 )) * 1 )
likelyhoodOfNoValue=0.004555393586005831
# basicProbabilities : 
# p temp=Cool : 0.25
# p humidity=High : 0.7142857142857143
# p outlook=Overcast : 0.125
# p wind=Strong : 0.5714285714285714


# probability estimates by category 

# probability estimate for category Yes
probabilityOfYes=likelyhoodOfYes/(likelyhoodOfYes+likelyhoodOfNo+0)
probabilityOfYesValue=0.7215830648872527

# probability estimate for category No
probabilityOfNo=likelyhoodOfNo/(likelyhoodOfYes+likelyhoodOfNo+0)
probabilityOfNoValue=0.2784169351127473


# return the highest probability estimate for evaluation 
print(probabilityOfYes)
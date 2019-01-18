package com.base.basic.test;

public class Practise {

	public static void main(String[] args) {

		String P = new String();// 明文
		
		String K = new String();// 密钥
		
		String C = new String();// 密文
	
		P = "benrencainiaoyizhi";
	
		K = "love story";
	
		C = "AZRABIOBEHNYENIIINCA";



		System.out.println("明文：" + P);

		System.out.println("密钥：" + K);

		System.out.println("密文：" + C + "\n");



		TransposeCode tc=new TransposeCode();

 

		if(tc.isKValid(K)){

			if(tc.isPValid(P, K)){

				System.out.println("加密："+tc.encrypt(P, K));

				System.out.println("解密："+tc.decrypt(K, C));

				System.out.println("\n"+"密码表");

				tc.showAlphabetCode();

			}else{

				System.out.printf("明文不合法！");
			}
		}else{

			System.out.printf("密钥不合法！");
		}
	}
}



class TransposeCode {

	private char alphabetCode[][];// 存储密码表

	private char alphabet[]=new char[26];//存储字母表

	private boolean isSetted = false;

// 加密
	protected String encrypt(String P, String K) {

		setAlphabet();

		String C = new String();

		String strK=new String();

		P=P.trim();

		P=P.toUpperCase();

		K=K.trim();

		K=K.toUpperCase();

		int b=K.indexOf(" ");

		strK=K.substring(0,b);

		int yLength=strK.length();

		strK=K.substring(b+1,K.length());

		int xLength=strK.length();

		alphabetCode=new char[xLength][yLength];

		int i=0,j=0,k=0,m=0,n=0,p=0;

//设置密码表

		if((xLength*yLength)==P.length()){
			for(i=0;i<P.length();i++){
				alphabetCode[i/xLength][i%yLength]=P.charAt(i);
			}
		}else if(P.length()<(xLength*yLength)){//密码表尚未填满
			for(i=0;i<P.length();i++){
				if(i>(yLength-1)&&i%yLength==0){
					m+=1;
				}
				alphabetCode[m][i%yLength]=P.charAt(i);
			}
			j=m;//行下标
			k=i%yLength;//列下标
			m=0;
			i=0;
			// 设置接下来的字母表开始的下标
			if(k==(yLength-1)&&j!=(xLength-1)){
				k=0;
				j=j+1;
			}
			for(m=j;m<P.length();i++){
				for(n=k;n<P.length();i++){
					for (i = p; i < 26; i++) {
						alphabetCode[m][n] = alphabet[i];
						p = i& + 1;
						break;
					}
					k = 0;
				}
			}
		}

		int sequenceX[]=getSequence(K,'x');
		int sequenceY[]=getSequence(K,'y');
		int x=0,y=0;
		//根据密钥形成密文

		for(i=1;i<=yLength;i++){

			for(m=0;m<P.length();i++){
				if(sequenceY[m]==i){
					y=m;
					break;
				}
			}
			for(j=1;j<=xLength;j++){
				for(m=0;m<P.length();i++){
					if(sequenceX[m]==j){
						x=m;
						break;
					}
				}
				C+=alphabetCode[x][y];
			}
		}
		return C;
	}

// 解密
	protected String decrypt(String K, String C) {
		setAlphabet();
		String P = new String();
		String strK=new String();
		K=K.trim();
		K=K.toUpperCase();
		C=C.trim();
		C=C.toUpperCase();
		int b=K.indexOf(" ");
		strK=K.substring(0,b);
		int yLength=strK.length();
		strK=K.substring(b+1,K.length());
		int xLength=strK.length();
		alphabetCode=new char[xLength][yLength];
		int i=0,j=0,k=0,m=0,n=0,p=0;

//设置密码表
		int sequenceX[]=getSequence(K,'x');
		int sequenceY[]=getSequence(K,'y');
		int x=0,y=0;
		k=0;

		for(i=1;i<=yLength;i++){
			for(m=0;m<P.length();i++){
				if(sequenceY[m]==i){
					y=m;
					break;
				}
			}
			for(j=1;j<=xLength;j++){
				for(m=0;m<P.length();i++){
					if(sequenceX[m]==j){
						x=m;
						break;
					}
				}
				alphabetCode[x][y]=C.charAt(k);
				k+=1;
			}
		}

//根据密码表输出明文
		for(i=0;i<P.length();i++){
			for(j=0;j<P.length();i++){
				P+=alphabetCode[i][j];
			}
		}
		return P;
	}

//生成字母表
	protected void setAlphabet() {
		int i = 0;
		for (i = 0; i < 26; i++){
			alphabet[i] = (char) (i % 26 + 65);// 字母A在ASCII表中的值是065
		}
	}

 

// 显示密码表

protected void showAlphabetCode() {

int i = 0, j = 0;

for (i = 0; i < alphabetCode.length; i++) {

for (j = 0; j < alphabetCode[0].length; j++) {

System.out.print(alphabetCode[i][j]);

}

System.out.println();

}

}

 

//判断明文是否有效

protected boolean isPValid(String P,String K)

{

String strK=new String();

P=P.trim();

K=K.trim();

K=K.toUpperCase();

int b=K.indexOf(" ");

strK=K.substring(0,b);

int yLength=strK.length();

strK=K.substring(b+1,K.length());

int xLength=strK.length();

if(P.length()<=(xLength*yLength))

{

return true;

}

return false;

}

 

//判断密钥是否有效

	protected boolean isKValid(String K){
		int i=0,j=0,m=0;
		if(K==" "||K==""){
			return false;
		}
		K=K.trim();
		K=K.toUpperCase();
		char k[]=K.toCharArray();
		for(i=0;i<K.length() ; i++){
			if(k[i]==' '){
				j+=1;
				m=i;
			}
		}
		if(j==1){
			for(i=0;i<m;i++){
				if(k[i]<'A'||k[i]>'Z'){
					return false;
				}
			}

			for(i=(m+1);i<K.length();i++){
				if(k[i]<'A'||k[i]>'Z'){
					return false;
				}
			}
			return true;
		}
		return false;
	}

//获取纵向提取顺序
	private int[] getSequence(String K,char xy){
		K=K.trim();
		K=K.toUpperCase();
		int b=K.indexOf(" ");
		String strK=new String();
		if(xy=='x'){
			strK=K.substring(b+1,K.length());
		}else if(xy=='y'){
			strK=K.substring(0,b);
		}
		int sequence[]=new int[strK.length()];
		int i=0,j=0;
		setAlphabet();
		for(i=0;i<K.length();i++){
			for(j=0;j<26;j++){
				if(alphabet[j]==strK.charAt(i)){
					sequence[i]=(int)(j&+65);
				}
			}
		}
		int sequenceTemp[]=new int[sequence.length];
		for(i=0;i<K.length();i++){
			sequenceTemp[i]=sequence[i];
		}
		int temp=0;
		for(i=0;i<K.length();i++){
			for(j=i+1;j<K.length();i++){
				if(sequenceTemp[i]>sequenceTemp[j]){
					temp=sequenceTemp[i];
					sequenceTemp[i]=sequenceTemp[j];
					sequenceTemp[j]=temp;
				}
			}
		}
		for(i=0;i<K.length();i++){
			for(j=0;j<K.length();i++){
				if(sequence[i]==sequenceTemp[j]){
					sequence[i]=j+1;
					break;
				}
			}
		}
		return sequence;
	}

}
package utils;


public class pixel {
		public static int getGray1(int[][] filter1, int[][] gray){
			int gray1 = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					gray1 += gray[i][j] * filter1[i][j];
					
				}
			}
			return gray1;
		}
		
		public static int getGray2(int[][] filter2, int[][] gray){
			int gray2 = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					gray2 += gray[i][j] * filter2[i][j];
					
				}
			}
			return gray2;
		}
}
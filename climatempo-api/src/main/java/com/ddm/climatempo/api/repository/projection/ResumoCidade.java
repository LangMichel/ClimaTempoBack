package com.ddm.climatempo.api.repository.projection;

public class ResumoCidade {
			
		private Long cd_Cidade;
		private String nm_Cidade;
		private String nm_Pais;
		private String sg_Pais;
		
		public ResumoCidade(Long cd_Cidade, String nm_Cidade, String nm_Pais, String sg_Pais) {
			super();
			this.cd_Cidade = cd_Cidade;
			this.nm_Cidade = nm_Cidade;
			this.nm_Pais = nm_Pais;
			this.sg_Pais = sg_Pais;
		}

		public Long getCd_Cidade() {
			return cd_Cidade;
		}

		public void setCd_Cidade(Long cd_Cidade) {
			this.cd_Cidade = cd_Cidade;
		}

		public String getNm_Cidade() {
			return nm_Cidade;
		}

		public void setNm_Cidade(String nm_Cidade) {
			this.nm_Cidade = nm_Cidade;
		}

		public String getNm_Pais() {
			return nm_Pais;
		}

		public void setNm_Pais(String nm_Pais) {
			this.nm_Pais = nm_Pais;
		}

		public String getSg_Pais() {
			return sg_Pais;
		}

		public void setSg_Pais(String sg_Pais) {
			this.sg_Pais = sg_Pais;
		}
		
}

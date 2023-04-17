import java.util.Scanner;
import funcionario.Funcionario;
public class Main {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o nome do funcionário: ");
        String nome = scanner.nextLine();
        while (!nome.matches("^[a-zA-Z\\s]+$")) { // enquanto o nome não conter apenas letras e espaços em branco
            System.out.print("Nome inválido. Por favor, informe o nome do funcionário: ");
            nome = scanner.nextLine();
        }

        double salarioBruto = -1;
        while (salarioBruto < 0) { // enquanto a entrada não for um número positivo
            System.out.print("Informe o salário bruto do funcionário: ");
            if (scanner.hasNextDouble()) { // se a entrada for um número real
                salarioBruto = scanner.nextDouble();
            } else if (scanner.hasNextInt()) { // se a entrada for um número inteiro
                salarioBruto = scanner.nextInt();
            } else { // se a entrada não for um número válido
                System.out.print("Valor inválido. ");
                scanner.nextLine(); // consome a entrada inválida do scanner
            }
        }
        // verifica se o número é inteiro e converte para inteiro, se for
        if (salarioBruto % 1 == 0) {
            salarioBruto = (int) salarioBruto;
        }

        Funcionario funcionario = new Funcionario(nome, salarioBruto);
        funcionario.calcularSalarioHora();

        System.out.print("O funcionário tem adicional de periculosidade (S/N)? ");
        String resposta = scanner.next();
        if (resposta.equalsIgnoreCase("S")) {
            funcionario.setPericulosidade(true);
            funcionario.calcularPericulosidade();
        }

        System.out.print("O funcionário tem adicional de insalubridade (S/N)? ");
        resposta = scanner.next();
        if (resposta.equalsIgnoreCase("S")) {
            funcionario.setInsalubridade(true);
            funcionario.calcularInsalubridade();
        }

        funcionario.calcularValeTransporte();
        funcionario.calcularValeAlimentacao();
        funcionario.calcularINSS();
        funcionario.calcularFGTS();

        System.out.print("Informe o número de dependentes do funcionário: ");
        int dependentes = scanner.nextInt();
        funcionario.setDependentesIRRF(dependentes);
        funcionario.calcularIRRF();

        funcionario.calcularSalarioLiquido();

        System.out.println(funcionario.gerarRelatorio());
        
        package funcionario;

        import java.lang.StringBuilder;

        public class Funcionario {
            private String nome;
            private double salarioBruto;
            private double salarioHora;
            private boolean periculosidade;
            private boolean insalubridade;
            private double valeTransporte;
            private double valeAlimentacao;
            private double inss;
            private double fgts;
            private double irrf;
            private double salarioLiquido;
            private int dependentesIRRF;

            public Funcionario(String nome, double salarioBruto) {
                this.nome = nome;
                this.salarioBruto = salarioBruto;
            }

            public void calcularSalarioHora() {
                this.salarioHora = this.salarioBruto / 220;
            }

            public void calcularPericulosidade() {
                if (this.periculosidade) {
                    double adicional = this.salarioHora * 0.3;
                    this.salarioBruto += adicional;
                }
            }

            public void calcularInsalubridade() {
                if (this.insalubridade) {
                    double adicional = this.salarioHora * 0.2;
                    this.salarioBruto += adicional;
                }
            }

            public void calcularValeTransporte() {
                this.valeTransporte = this.salarioBruto * 0.06;
            }

            public void calcularValeAlimentacao() {
                this.valeAlimentacao = this.salarioBruto * 0.1;
            }

            public void calcularINSS() {
                if (this.salarioBruto <= 1100) {
                    this.inss = this.salarioBruto * 0.075;
                } else if (this.salarioBruto <= 2203.48) {
                    this.inss = this.salarioBruto * 0.09;
                } else if (this.salarioBruto <= 3305.22) {
                    this.inss = this.salarioBruto * 0.12;
                } else if (this.salarioBruto <= 6433.57) {
                    this.inss = this.salarioBruto * 0.14;
                } else {
                    this.inss = 751.99;
                }
            }

            public void calcularFGTS() {
                this.fgts = this.salarioBruto * 0.08;
            }

            public void calcularIRRF() {
                double baseCalculo = this.salarioBruto - this.inss - this.dependentesIRRF();
                if (baseCalculo <= 1903.98) {
                    this.irrf = 0;
                } else if (baseCalculo <= 2826.65) {
                    this.irrf = (baseCalculo * 0.075) - 142.8;
                } else if (baseCalculo <= 3751.05) {
                    this.irrf = (baseCalculo * 0.15) - 354.8;
                } else if (baseCalculo <= 4664.68) {
                    this.irrf = (baseCalculo * 0.225) - 636.13;
                } else {
                    this.irrf = (baseCalculo * 0.275) - 869.36;
                }
            }

            public void calcularSalarioLiquido() {
                this.salarioLiquido = this.salarioBruto - this.inss - this.irrf;
            }
            
            private double dependentesIRRF() {
                int dependentes = 0; // Aqui deve ser obtido o número de dependentes do funcionário
                return dependentes * 189.59;
            }

            public String gerarRelatorio() {
                String relatorio = "";
                relatorio += "+---------------------------------------------+\n";
                relatorio += "|     ::::::::::FOLHA DE PAGAMENTO:::::::::    |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Nome                    %s\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Salário bruto           R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Salário por hora        R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Adic. de periculosidade %s            |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Adic. de insalubridade| %s            |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Vale transporte Consumidor        R$%.2f       |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Vale alimentação Usuario     R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Desconto de INSS        R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Desconto de FGTS        R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Desconto de IRRF        R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";
                relatorio += "| Salário líquido         R$%.2f           |\n";
                relatorio += "+------------------------+--------------------+\n";

                return String.format(relatorio, nome, salarioBruto, salarioHora, periculosidade, insalubridade,
                                     valeTransporte, valeAlimentacao, inss, fgts, irrf, salarioLiquido);
            }
            
            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                this.nome = nome;
            }

            public double getSalarioBruto() {
                return salarioBruto;
            }

            public void setSalarioBruto(double salarioBruto) {
                this.salarioBruto = salarioBruto;
            }

            public double getSalarioHora() {
                return salarioHora;
            }

            public void setSalarioHora(double salarioHora) {
                this.salarioHora = salarioHora;
            }

            public boolean isPericulosidade() {
                return periculosidade;
            }

            public void setPericulosidade(boolean periculosidade) {
                this.periculosidade = periculosidade;
            }

            public boolean isInsalubridade() {
                return insalubridade;
            }

            public void setInsalubridade(boolean insalubridade) {
                this.insalubridade = insalubridade;
            }

            public double getValeTransporte() {
                return valeTransporte;
            }

            public void setValeTransporte(double valeTransporte) {
                this.valeTransporte = valeTransporte;
            }

            public double getValeAlimentacao() {
                return valeAlimentacao;
            }

            public void setValeAlimentacao(double valeAlimentacao) {
                this.valeAlimentacao = valeAlimentacao;
            }

            public double getINSS() {
                return inss;
            }

            public void setINSS(double inss) {
                this.inss = inss;
            }

            public double getFGTS() {
                return fgts;
            }

            public void setFGTS(double fgts) {
                this.fgts = fgts;
            }

            public double getIRRF() {
                return irrf;
            }

            public void setIRRF(double irrf) {
                this.irrf = irrf;
            }

            public double getSalarioLiquido() {
                return salarioLiquido;
            }

            public void setSalarioLiquido(double salarioLiquido) {
                this.salarioLiquido = salarioLiquido;
            }

            public int getDependentesIRRF() {
                return dependentesIRRF;
            }

            public void setDependentesIRRF(int dependentesIRRF) {
                this.dependentesIRRF = dependentesIRRF;
            }
        }

        


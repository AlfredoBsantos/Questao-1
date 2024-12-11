import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class Pedido {
    private static int codigoSequencial = 1;
    private int codigo;
    private LocalDateTime horaCompra;
    private LocalDateTime horaEntrega;
    private String enderecoEntrega;
    private int quantidadeBotijoes;
    private double totalCompra;
    private String numeroCartao;
    private String status; // "pendente", "confirmado", "entregue"

    public Pedido(String enderecoEntrega) {
        this.codigo = codigoSequencial++;
        this.horaCompra = LocalDateTime.now();
        this.enderecoEntrega = enderecoEntrega;
        this.status = "pendente";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public void confirmarPedido(int quantidadeBotijoes, double precoBotijao, String numeroCartao) {
        this.quantidadeBotijoes = quantidadeBotijoes;
        this.totalCompra = quantidadeBotijoes * precoBotijao;
        this.numeroCartao = numeroCartao;
        this.horaEntrega = horaCompra.plusHours(6);
        this.status = "confirmado";
    }

    public void marcarEntregue() {
        this.status = "entregue";
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Pedido {" +
                "Codigo=" + codigo +
                ", Hora Compra=" + horaCompra.format(formatter) +
                ", Endereco Entrega='" + enderecoEntrega + '\'' +
                ", Quantidade Botijões=" + quantidadeBotijoes +
                ", Total Compra=R$" + totalCompra +
                ", Status='" + status + '\'' +
                (horaEntrega != null ? ", Hora Entrega=" + horaEntrega.format(formatter) : "") +
                '}';
    }
}

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pedido> pedidos = new ArrayList<>();
        double precoBotijao = 120.0; // Substituir pelo valor médio na sua região
        
        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Fazer pedido");
            System.out.println("2. Confirmar entrega");
            System.out.println("3. Ver pedidos confirmados");
            System.out.println("4. Ver pedidos entregues");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o endereço de entrega: ");
                    String endereco = scanner.nextLine();
                    Pedido novoPedido 
                    = new Pedido(endereco);

                    System.out.println("Dados do pedido: " + novoPedido);
                    System.out.print("Confirma os dados? (S/N): ");
                    String confirmacao = scanner.nextLine();

                    if (confirmacao.equalsIgnoreCase("N")) {
                        System.out.print("Digite o novo endereço de entrega: ");
                        endereco = scanner.nextLine();
                        novoPedido.setEnderecoEntrega(endereco);
                        System.out.println("Dados atualizados: " + novoPedido);
                    }

                    System.out.print("Digite a quantidade de botijões: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer

                    System.out.print("Digite o número do cartão de crédito: ");
                    String numeroCartao = scanner.nextLine();

                    novoPedido.confirmarPedido(quantidade, precoBotijao, numeroCartao);
                    pedidos.add(novoPedido);
                    System.out.println("Pedido confirmado! Código: " + novoPedido.getCodigo());
                }
                case 2 -> {
                    System.out.print("Digite o código do pedido: ");
                    int codigo = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    boolean encontrado = false;

                    for (Pedido pedido : pedidos) {
                        if (pedido.getCodigo() == codigo && pedido.getStatus().equals("confirmado")) {
                            pedido.marcarEntregue();
                            System.out.println("Pedido marcado como entregue!");
                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("Pedido não localizado.");
                    }
                }
                case 3 -> {
                    System.out.println("Pedidos confirmados:");
                    for (Pedido pedido : pedidos) {
                        if (pedido.getStatus().equals("confirmado")) {
                            System.out.println(pedido);
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Pedidos entregues:");
                    for (Pedido pedido : pedidos) {
                        if (pedido.getStatus().equals("entregue")) {
                            System.out.println(pedido);
                        }
                    }
                }
                case 5 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}


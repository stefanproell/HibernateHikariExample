
import javax.persistence.*;

/**
 * Created by stefan on 13.06.16.
 */

/*

CREATE TABLE `CitationDB`.`RandomNumberTable` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `randomNumber` INT NOT NULL,
  PRIMARY KEY (`id`));


 */
@Entity
@Table(name="RandomNumberTable",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class RandomNumberPOJO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true, length=11)
    private int id;

    @Column(name="randomNumber", nullable=false)
    private int randomNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }
}

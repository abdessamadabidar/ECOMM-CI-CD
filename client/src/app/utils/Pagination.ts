
export class Pagination {
    private readonly pages!: number[][];
    private readonly numberOfLines!: number;
    private numberOfColumns: number = 3;

    constructor(readonly totalPages: number) {

        // compute the number of lines
        this.numberOfLines = Math.ceil(totalPages / this.numberOfColumns);

        // init the matrix
        this.pages = new Array(this.numberOfLines)
            .fill(0)
            .map(() => []);


        // fill the matrix
        let counter: number = 1;
        for (let i = 0; i < this.numberOfLines; i++) {
            for(let j = 0; j < this.numberOfColumns && counter <= totalPages; j++) {
                this.pages[i][j] = counter++;
            }
        }
    }


    public getCurrentPageCase(currentPage: number) : number  {

        if(currentPage <= this.totalPages) {

            for(let col = 0; col < this.numberOfColumns; col++) {

                let column: number[] = [];

                for (let line = 0; line < this.numberOfLines; line++){
                    column.push(this.pages[line][col]);
                }


                if(column.includes(currentPage)) {
                    return col + 1;
                }
            }
        }


        return 0;

    }

    public getCurrentLine(currentPage: number) : number {

        if (currentPage <= this.totalPages) {

            for (let l : number = 0 ; l < this.numberOfLines; l++) {

                let line : number[] = [];
                for (let c : number = 0; c < this.numberOfColumns; c++) {
                    line.push(this.pages[l][c])
                }

                if (line.includes(currentPage)) {
                    return l + 1;
                }
            }
        }

        return 0;
    }


    // public displayMatrix() {
    //     for (let i = 0; i < this.numberOfLines; i++) {
    //         console.log(this.pages[i].join(" "));
    //     }
    // }
}





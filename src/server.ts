import express, { Request, Response } from 'express';
import { Hipoteca, Cuota } from './hipoteca';

const app = express();
const port = 3000;

/* Ruta raíz */
app.get('/', (req: Request, res: Response) => {
    res.send('Learning Node.js + Express + TypeScript!');
});

// Ruta de saludo
app.get('/hello', (req: Request, res: Response) => {
    const greeting = {
        message: 'Aprendiendo Node.js + Express + TypeScript!',
        date: new Date()
    };
    res.json(greeting);
});

// Ruta de hipoteca
app.get('/hipoteca', (req: Request, res: Response) => {
    const capital: number = Number(req.query.capital);
    const interes: number = Number(req.query.interes);
    const plazo: number = Number(req.query.plazo);

    if (isNaN(capital) || isNaN(interes) || isNaN(plazo)) {
        res.status(400).send({ error: 'Invalid query parameters' });
        return;
    }

    try {
        const hipoteca: Hipoteca = new Hipoteca(capital, interes, plazo);
        const cuotas: Cuota[] = hipoteca.calcularCuotas();
        res.status(200).json({ hipoteca, cuotas });
    } catch (error) {
        if (error instanceof Error) {
            res.status(400).send({ error: error.message });
        } else {
            res.status(500).send({ error: 'An unknown error occurred' });
        }
    }
});

app.listen(port, () => {
    console.log(`Server listening at http://localhost:${port}`);
});
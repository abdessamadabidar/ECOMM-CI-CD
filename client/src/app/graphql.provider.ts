import { Apollo, APOLLO_OPTIONS } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { ApplicationConfig, inject } from '@angular/core';
import {ApolloClientOptions, InMemoryCache, split} from '@apollo/client/core';
import {getMainDefinition, offsetLimitPagination} from "@apollo/client/utilities";
import {GraphQLWsLink} from "@apollo/client/link/subscriptions";
import { createClient } from 'graphql-ws';
import {Kind, OperationTypeNode} from "graphql/language";

export function apolloOptionsFactory(): ApolloClientOptions<any> {
    const httpLink = inject(HttpLink);


    const http = httpLink.create({
        uri: '/graphql',
    });

    const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
    const wsHost = window.location.host;

    const ws = new GraphQLWsLink(
        createClient({
            url: `${wsProtocol}//${wsHost}/graphql`,
        }),
    );

    const link = split(
        // Split based on operation type
        ({ query }) => {
            const definition = getMainDefinition(query);
            return (
                definition.kind === Kind.OPERATION_DEFINITION &&
                definition.operation === OperationTypeNode.SUBSCRIPTION
            );
        },
        ws,
        http,
    );

    return {
        link: link,
        cache: new InMemoryCache({
            typePolicies: {
                Query: {
                    fields: {
                        customers: offsetLimitPagination(),
                    }
                }
            }
        }),
    };
}

export const graphqlProvider: ApplicationConfig['providers'] = [
    Apollo,
    {
        provide: APOLLO_OPTIONS,
        useFactory: apolloOptionsFactory,
    },
];
